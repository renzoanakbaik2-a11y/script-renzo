-- ==========================================================
-- SPEED HUB X - MODULE FUNCTION & TRIGGER INSPECTOR
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local ReplicatedStorage = game:GetService("ReplicatedStorage")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("ModuleInspectorUI") then
    PlayerGui.ModuleInspectorUI:Destroy()
end

-- 1. SCREEN GUI UTAMA
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "ModuleInspectorUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 360, 0, 220)
MainFrame.Position = UDim2.new(0.5, -180, 0.4, -110)
MainFrame.BackgroundColor3 = Color3.fromRGB(18, 18, 24)
MainFrame.Active = true
MainFrame.Draggable = true
MainFrame.Parent = ScreenGui

local Corner = Instance.new("UICorner")
Corner.CornerRadius = UDim.new(0, 8)
Corner.Parent = MainFrame

local Stroke = Instance.new("UIStroke")
Stroke.Color = Color3.fromRGB(255, 30, 60)
Stroke.Thickness = 2
Stroke.Parent = MainFrame

local Title = Instance.new("TextLabel")
Title.Size = UDim2.new(1, -30, 0, 28)
Title.Position = UDim2.new(0, 10, 0, 0)
Title.BackgroundTransparency = 1
Title.Text = "Module & Trigger Inspector"
Title.TextColor3 = Color3.fromRGB(255, 255, 255)
Title.Font = Enum.Font.SourceSansBold
Title.TextSize = 12
Title.TextXAlignment = Enum.TextXAlignment.Left
Title.Parent = MainFrame

local CloseBtn = Instance.new("TextButton")
CloseBtn.Size = UDim2.new(0, 20, 0, 20)
CloseBtn.Position = UDim2.new(1, -24, 0, 4)
CloseBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
CloseBtn.Text = "X"
CloseBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
CloseBtn.Font = Enum.Font.SourceSansBold
CloseBtn.TextSize = 10
CloseBtn.Parent = MainFrame

local CloseCorner = Instance.new("UICorner")
CloseCorner.CornerRadius = UDim.new(0, 4)
CloseCorner.Parent = CloseBtn

CloseBtn.MouseButton1Click:Connect(function()
    ScreenGui:Destroy()
end)

local Scroll = Instance.new("ScrollingFrame")
Scroll.Size = UDim2.new(1, -16, 1, -40)
Scroll.Position = UDim2.new(0, 8, 0, 32)
Scroll.BackgroundColor3 = Color3.fromRGB(12, 12, 16)
Scroll.BorderSizePixel = 0
Scroll.ScrollBarThickness = 3
Scroll.ScrollBarImageColor3 = Color3.fromRGB(255, 30, 60)
Scroll.Parent = MainFrame

local ScrollCorner = Instance.new("UICorner")
ScrollCorner.CornerRadius = UDim.new(0, 6)
ScrollCorner.Parent = Scroll

local Layout = Instance.new("UIListLayout")
Layout.Padding = UDim.new(0, 4)
Layout.SortOrder = Enum.SortOrder.LayoutOrder
Layout.Parent = Scroll

local function AddLog(title, desc)
    local Label = Instance.new("TextLabel")
    Label.Size = UDim2.new(1, -8, 0, 28)
    Label.BackgroundTransparency = 1
    Label.Text = "• " .. title .. "\n  " .. desc
    Label.TextColor3 = Color3.fromRGB(255, 100, 120)
    Label.Font = Enum.Font.SourceSansSemibold
    Label.TextSize = 10
    Label.TextXAlignment = Enum.TextXAlignment.Left
    Label.TextYAlignment = Enum.TextYAlignment.Top
    Label.Parent = Scroll
end

-- 2. INSPEKSI MODUL INTERNAL (RandomFish & Rolling)
local Modules = ReplicatedStorage:FindFirstChild("Modules")
if Modules then
    for _, mod in ipairs(Modules:GetDescendants()) do
        if mod:IsA("ModuleScript") and (mod.Name == "RandomFish" or mod.Name == "Rolling") then
            local ok, loaded = pcall(require, mod)
            if ok and type(loaded) == "table" then
                for k, v in pairs(loaded) do
                    if type(v) == "function" and (k == "Roll" or k == "Rollable") then
                        local info = debug.info(v, "a") -- Cek jumlah parameter fungsi
                        AddLog("Modul: " .. mod.Name .. " -> " .. k .. "()", "Membutuhkan Parameter: " .. tostring(info) .. " argumen")
                    end
                end
            end
        end
    end
end

-- 3. INSPEKSI OBJEK PEMICU DI TOMBOL MERAH WORKSPACE
local triggerCount = 0
for _, desc in ipairs(Workspace:GetDescendants()) do
    if desc:IsA("ClickDetector") or desc:IsA("ProximityPrompt") or desc:IsA("TouchTransmitter") then
        local pName = string.lower(desc.Parent.Name)
        if string.find(pName, "roll") or string.find(pName, "button") or string.find(pName, "fisherman") or string.find(pName, "stand") then
            triggerCount = triggerCount + 1
            AddLog("Pemicu Fisik: " .. desc.ClassName, "Lokasi: " .. desc.Parent:GetFullName())
        end
    end
end

if triggerCount == 0 then
    AddLog("Pemicu Fisik", "Tidak ditemukan objek ClickDetector/Proximity di Workspace.")
end

Scroll.CanvasSize = UDim2.new(0, 0, 0, Layout.AbsoluteContentSize.Y + 10)
